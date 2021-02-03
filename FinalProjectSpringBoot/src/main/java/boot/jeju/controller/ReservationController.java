package boot.jeju.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import boot.jeju.data.RentCarDto;
import boot.jeju.mapper.ReservationMapper;

public class ReservationController {
	@Autowired
	ReservationMapper mapper;
	
	@PostMapping("/reservation/carlist")
	public List<RentCarDto> getCarlist(){
		return mapper.getListOfRentCar();
	}
}
