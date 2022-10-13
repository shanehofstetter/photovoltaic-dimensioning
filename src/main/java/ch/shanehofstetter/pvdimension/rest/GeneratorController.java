package ch.shanehofstetter.pvdimension.rest;

import ch.shanehofstetter.pvdimension.PVDimensionApplication;
import ch.shanehofstetter.pvdimension.pvgenerator.PVGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shane Hofstetter - shane.hofstetter@gmail.com<br>
 */
@RestController
public class GeneratorController {

    private PVGenerator pvGenerator = PVDimensionApplication.getPvGenerator();

    @RequestMapping(method = RequestMethod.GET, value = "/pv-generator")
    public PVGenerator getPvGenerator(){
        return pvGenerator;
    }

}
