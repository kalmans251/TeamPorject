package com.himedia;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.himedia.item.entity.Color;
import com.himedia.item.entity.ColorRepository;
import com.himedia.item.entity.Size;
import com.himedia.item.entity.SizeRepository;
import com.himedia.item.entity.enums.ColorEnum;
import com.himedia.item.entity.enums.SizeEnum;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TeamProjectApplication implements CommandLineRunner {
	
	private final SizeRepository sizeRepository;
	private final ColorRepository colorRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(TeamProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
			String s = null;
			for(int i = 0 ; i < SizeEnum.values().length ; i++) {
				Size size= new Size();
				s = SizeEnum.values()[i].getValue();
				size.setName(s);
				this.sizeRepository.save(size);
			}
			for(int i = 0 ; i < ColorEnum.values().length ; i++) {
				Color color  = new Color();
				s = ColorEnum.values()[i].getValue();
				color.setName(s);
				this.colorRepository.save(color);
			}
			
		}

	

}
