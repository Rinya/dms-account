package ru.alfastrah.account.sber.backend.model.policy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alfastrah.account.sber.backend.TestBackendConfiguration;
import ru.alfastrah.account.sber.backend.converter.XmlConverter;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBackendConfiguration.class})
public class RelativesTest {
    @Autowired
    private XmlConverter converter;

    @Test
    public void policyMarshall() {
        Relatives relatives = new Relatives();
        relatives.getRelativeList().add(createRelative(53661915L, BigDecimal.valueOf(300), "Y"));
        relatives.getRelativeList().add(createRelative(53784988L, BigDecimal.valueOf(23000), "N"));
        relatives.getRelativeList().add(createRelative(53639123L, BigDecimal.valueOf(500), "Y"));

        System.out.println(converter.doMarshaling(relatives));
    }

    @Test
    public void dentalMatshal() {
        Relatives relatives = new Relatives();
        relatives.getRelativeList().add(createDental(53661915L, BigDecimal.valueOf(300), 12L));
        relatives.getRelativeList().add(createDental(53784988L, BigDecimal.valueOf(23000), 13L));
        relatives.getRelativeList().add(createDental(53639123L, BigDecimal.valueOf(500), 14L));

        System.out.println(converter.doMarshaling(relatives));
    }

    private Relative createRelative(long userID, BigDecimal premium, String changeProgramm) {
        Relative relative = new Relative();
        relative.setUserID(userID);
        relative.setPremium(premium);
        relative.setChangeProgramm(changeProgramm);
        return relative;
    }

    private Relative createDental(long userID, BigDecimal premium, Long programmId) {
        Relative relative = new Relative();
        relative.setUserID(userID);
        relative.setPremium(premium);
        relative.setProgrammId(programmId);
        return relative;
    }
}