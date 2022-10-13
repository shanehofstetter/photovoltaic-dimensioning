package ch.shanehofstetter.pvdimension.rest;

import ch.shanehofstetter.pvdimension.PVDimensionApplication;
import ch.shanehofstetter.pvdimension.pvgenerator.Battery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
@RestController
public class BatteryController {

    private Battery battery = PVDimensionApplication.getPvGenerator().getBattery();

    @RequestMapping(method = RequestMethod.GET, value = "/pv-generator/battery")
    public Battery getBattery(){
        return battery;
    }


}
