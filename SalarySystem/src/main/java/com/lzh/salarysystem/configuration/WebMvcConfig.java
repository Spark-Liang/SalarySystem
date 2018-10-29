package com.lzh.salarysystem.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lzh.salarysystem.controller.BaseController;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
	"com.lzh.salarysystem.controller","com.lzh.salarysystem.configuration"
})
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	
	public static final String ROOT_PATH = "/";
	public static final String INDEX_PATH = "/index";
	
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController(ROOT_PATH, INDEX_PATH);
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
	
	@Controller
	public static class DefaultPageController extends BaseController{
		
		@RequestMapping(INDEX_PATH)
		public ModelAndView page(ModelMap model) {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addAllObjects(model);
			modelAndView.setViewName("index");
			return modelAndView;
		}
	}
}
