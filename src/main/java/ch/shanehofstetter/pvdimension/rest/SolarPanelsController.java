package ch.shanehofstetter.pvdimension.rest;

import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelDatabase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
@RestController
public class SolarPanelsController {
    private List<SolarPanel> solarPanels;

    public SolarPanelsController() {
        SolarPanelDatabase.load();
        solarPanels = SolarPanelDatabase.getSolarPanels();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/solarpanels")
    public List<SolarPanel> getSolarPanels() {
        return solarPanels.subList(0, 20);
    }
}
