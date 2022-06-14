package com.example.mas_gui;

import com.example.mas_gui.model.*;
import com.example.mas_gui.model.enums.CertificatesLevels;
import com.example.mas_gui.model.enums.PackStatus;
import com.example.mas_gui.repository.*;
import com.example.mas_gui.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class MasGuiApplication implements CommandLineRunner {
    SenderService senderService;
    @Autowired
    EmployeeRepository empRepo;
    @Autowired
    SenderRepository senderRepo;
    @Autowired
    PackRepository packRepo;
    @Autowired
    LocationRepository locationRepo;
    @Autowired
    RouteRepository routeRepo;
    @Autowired
    VehicleRepository vehicleRepo;
    public MasGuiApplication(SenderService senderService){
        this.senderService = senderService;
    }
    public static void main(String[] args) {
        SpringApplication.run(MasGuiApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {

        Vehicle vehicle = new Vehicle(313231.2,2012, true, "opis techniczny samochdodu","3123123");
        Courier courier= new Courier("Michal","Malczewski",
                LocalDate.of(2018,3,14),5700.0,LocalDate.of(1987,9,16),
                LocalDate.of(2023,5,30));
        courier.getVehicles().add(vehicle);

        Sender sender = new IndividualClient("535322111","jan@wp.pl","Kowalski",LocalDate.now());
        Sender sender2 = new IndividualClient("901223145","nowakPiotr@onet.com","Nowak",LocalDate.now());
        Sender sender3 = new Firm("783145992","apple@gmail.pl","Apple","91098322333","12124124124");
        Sender sender4 = new Firm("602134569","fb123@gmail.pl","Facebook","12353253","3252356345");

        Location location = new Location("Warsaw","Krucza","05-902");
        Home home= new Home("Lublin","Podlesna","05-211",21,null);
        Home home2= new Home("Kraków","Złota","02-100",44,509);
        ParcelLocker parcelLocker = new ParcelLocker("Poznan","Mandarynki","08-980",43l,23);
        Logistician logistician = new Logistician("Jan","Kowalski",LocalDate.now(),4300.5,LocalDate.now(),List.of(CertificatesLevels.LOGISTIC_BASIC,CertificatesLevels.LOGISTIC_EXPERT));
        Pack pack1 =new Pack(2,24.0,PackStatus.ODEBRANA,9,courier,sender,location);
        Pack pack2 =new Pack(33,90.0, PackStatus.NADANA,340,courier,sender4,location);
        Pack pack3 =new Pack(18,67.5, PackStatus.PRZYPISANA,340,courier,sender4,location);
        Pack pack4 =new Pack(41,449.9, PackStatus.DOSTARCZONA,340,courier,sender3,location);
        Pack pack5 =new Pack(6,12.9, PackStatus.ANULOWANA,340,courier,sender2,location);
        Route route = new Route(12.5, LocalDate.now(),courier,vehicle);


        empRepo.save(courier);
        empRepo.save(logistician);
        senderRepo.saveAll(List.of(sender,sender2,sender3,sender4));
        locationRepo.save(location);
        packRepo.saveAll(List.of(pack1,pack2,pack3,pack4,pack5));
        vehicleRepo.save(vehicle);
        routeRepo.save(route);
    }


}
