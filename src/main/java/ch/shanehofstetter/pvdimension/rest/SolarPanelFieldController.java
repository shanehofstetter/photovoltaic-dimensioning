package ch.shanehofstetter.pvdimension.rest;

import ch.shanehofstetter.pvdimension.PVDimensionApplication;
import ch.shanehofstetter.pvdimension.pvgenerator.solarpanel.SolarPanelField;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
@RestController
public class SolarPanelFieldController {

    private SolarPanelField solarPanelField = PVDimensionApplication.getPvGenerator().getSolarPanelField();

    @RequestMapping(method = RequestMethod.GET, value = "/pv-generator/solarpanel-field")
    public SolarPanelField getSolarPanelField(){
        return solarPanelField;
    }

}
