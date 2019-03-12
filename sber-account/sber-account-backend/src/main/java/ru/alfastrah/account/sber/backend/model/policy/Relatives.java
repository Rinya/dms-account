package ru.alfastrah.account.sber.backend.model.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "Relatives")
@XmlAccessorType(XmlAccessType.NONE)
public class Relatives {
    @XmlElement(name = "Relative")
    private List<Relative> relativeList;

    public List<Relative> getRelativeList() {
        if (relativeList == null) {
            relativeList = new LinkedList<>();
        }
        return relativeList;
    }

    public void setRelativeList(List<Relative> relativeList) {
        this.relativeList = relativeList;
    }

    @Override
    public String toString() {
        return "Relatives{" +
                "relativeList=" + relativeList +
                '}';
    }
}
