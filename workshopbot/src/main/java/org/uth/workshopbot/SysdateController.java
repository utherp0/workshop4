package org.uth.workshopbot;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SysdateController 
{
	@RequestMapping("/sysdate")
	public String getDate() 
	{
    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss.SSS");

    String dateString = format.format( new Date());

		return dateString;
	}
}
