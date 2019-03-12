package ru.alfastrah.account.sber.view.component;

import com.vaadin.server.*;
import com.vaadin.ui.AbstractComponent;
import ru.alfastrah.account.sber.backend.HasLogger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class AdvancedFileDownloader extends FileDownloader implements HasLogger {
    private static final boolean DEBUG_MODE = true;

    private AbstractComponent extendedComponent;
    private AdvancedDownloaderListener dynamicDownloaderListener;
    private DownloaderEvent downloadEvent;
    private String fileName;

    public interface DownloaderEvent extends Serializable {
        AbstractComponent getExtendedComponent();

        void setExtendedComponent(AbstractComponent extendedComponent);
    }

    public interface AdvancedDownloaderListener extends Serializable {
        void beforeDownload(DownloaderEvent downloaderEvent);
    }

    public AdvancedFileDownloader() {
        super(new StreamResource(null, ""));
        getLogger().info("created a new instance of resource: {}", this.getFileDownloadResource());
    }

    public void addAdvancedDownloaderListener(AdvancedDownloaderListener listener) {
        if (listener != null) {
            DownloaderEvent downloaderEvent = new DownloaderEvent() {
                @Override
                public AbstractComponent getExtendedComponent() {
                    return extendedComponent;
                }

                @Override
                public void setExtendedComponent(AbstractComponent extendedComponent) {
                    AdvancedFileDownloader.this.extendedComponent = extendedComponent;
                }
            };

            downloaderEvent.setExtendedComponent(this.extendedComponent);
            dynamicDownloaderListener = listener;
            this.downloadEvent = downloaderEvent;
        }
    }

    @Override
    public boolean handleConnectorRequest(VaadinRequest request, VaadinResponse response, String path) throws IOException {
        if (!path.matches("dl(/.*)?")) {
            return false;
        }

        getLogger().info("path {}", path);

        VaadinSession session = getSession();
        session.lock();
        fireEvent();

        DownloadStream stream;

        try {
            Resource resource = getFileDownloadResource();
            if (!(resource instanceof ConnectorResource)) {
                return false;
            }
            stream = ((ConnectorResource) resource).getStream();

            stream.setFileName(fileName);
            if (stream.getParameter("Content-Disposition") == null) {
                stream.setParameter("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            }

            if (isOverrideContentType()) {
                stream.setContentType("application/octet-stream;charset=UTF-8");
            }
        } finally {
            session.unlock();
        }

        stream.writeResponse(request, response);
        return true;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    void setResource(ByteArrayOutputStream stream, String fileName) {
        this.fileName = fileName;
        try {
            setFileDownloadResource(new StreamResource((StreamResource.StreamSource) () ->
                    new ByteArrayInputStream(stream.toByteArray()), fileName));
        } catch (Exception e) {
            getLogger().error("setting resource exception {}", e);
        }
    }

    private void fireEvent() {
        if (DEBUG_MODE) {
            getLogger().info("inside fireEvent");
        }

        if (dynamicDownloaderListener != null && downloadEvent != null) {
            if (DEBUG_MODE) {
                getLogger().info("beforeDownload is going to be invoked");
            }

            dynamicDownloaderListener.beforeDownload(downloadEvent);
        }
    }
}
