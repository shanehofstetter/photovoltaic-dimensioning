package ch.shanehofstetter.pvdimension.rest;

import ch.shanehofstetter.pvdimension.PVDimensionApplication;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanel;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
@RestController
public class SolarPanelController {

    private SolarPanel solarPanel = PVDimensionApplication.getPvGenerator().getSolarPanelField().getSolarPanel();

    @RequestMapping(method = RequestMethod.GET, value = "/pv-generator/solarpanel-field/solarpanel")
    public SolarPanel getSolarPanel(){
        return solarPanel;
    }

}
