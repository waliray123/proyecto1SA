package com.eatsleep.hotel.room.infrastructure.inputadapters.restapi;

import com.eatsleep.hotel.common.WebAdapter;
import com.eatsleep.hotel.maintenance.domain.Maintenance;
import com.eatsleep.hotel.maintenance.infrastructure.inputports.MakeMaintenanceAllRoomsInputPort;
import com.eatsleep.hotel.maintenance.infrastructure.inputports.MakeMaintenanceRoomInputPort;
import com.eatsleep.hotel.room.application.createroomusecase.CreateRoomRequest;
import com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response.CreateRoomResponse;
import com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response.RetrieveRoomResponse;
import com.eatsleep.hotel.room.application.updateroomusecase.UpdateRoomRequest;
import com.eatsleep.hotel.room.domain.Room;
import com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response.MaintenanceRoomResponse;
import com.eatsleep.hotel.room.infrastructure.inputadapters.restapi.response.UpdateRoomResponse;
import com.eatsleep.hotel.room.infrastructure.inputports.CheckInRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.inputports.CheckOutRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.inputports.CreateRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.inputports.ExistRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.inputports.RetrieveRoomInputPort;
import com.eatsleep.hotel.room.infrastructure.inputports.UpdateRoomInputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/rooms")
@WebAdapter
public class RoomControllerAdapter {

    private CreateRoomInputPort createRoomInputPort;
    private UpdateRoomInputPort updateRoomInputPort;
    private RetrieveRoomInputPort retrieveRoomInputPort;
    private MakeMaintenanceRoomInputPort maintenanceRoomInputPort;
    private MakeMaintenanceAllRoomsInputPort maintenanceAllRoomsInputPort;
    private ExistRoomInputPort existRoomInputPort;
    private CheckInRoomInputPort checkInRoomInputPort;
    private CheckOutRoomInputPort checkOutRoomInputPort;

    @Autowired
    public RoomControllerAdapter(CreateRoomInputPort createRoomInputPort, UpdateRoomInputPort updateRoomInputPort
            , RetrieveRoomInputPort retrieveRoomInputPort
            , MakeMaintenanceRoomInputPort maintenanceRoomInputPort
            , MakeMaintenanceAllRoomsInputPort maintenanceAllRoomsInputPort 
            , CheckInRoomInputPort checkInRoomInputPort
            , CheckOutRoomInputPort checkOutRoomInputPort
            , ExistRoomInputPort existRoomInputPort) {
        this.createRoomInputPort = createRoomInputPort;
        this.updateRoomInputPort = updateRoomInputPort;
        this.retrieveRoomInputPort = retrieveRoomInputPort;
        this.maintenanceRoomInputPort = maintenanceRoomInputPort;
        this.maintenanceAllRoomsInputPort = maintenanceAllRoomsInputPort;
        this.checkInRoomInputPort = checkInRoomInputPort;
        this.checkOutRoomInputPort = checkOutRoomInputPort;
        this.existRoomInputPort = existRoomInputPort;
    }
    
    
    @PostMapping("/save")
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest room) {
        Room createdRoom = createRoomInputPort.createRoom(room);
        return new ResponseEntity<>(new CreateRoomResponse(createdRoom), HttpStatus.CREATED);
    }
    
    @GetMapping("/getroom/{id}")
    public ResponseEntity<RetrieveRoomResponse> getRoomById(@PathVariable String id) {
        Optional<Room> roomOptional = retrieveRoomInputPort.getRoomById(id);
        return roomOptional.map(room -> new ResponseEntity<>(new RetrieveRoomResponse(room), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveRoomResponse>> getAllRooms() {
        List<Room> rooms = retrieveRoomInputPort.getAllRooms();
        List<RetrieveRoomResponse> roomsResponse = rooms.stream()
                .map(RetrieveRoomResponse::from)
                .collect(Collectors.toList());
        return new ResponseEntity<>(roomsResponse, HttpStatus.OK);
    }
    
    @PutMapping("/update/{id}")
    public ResponseEntity<UpdateRoomResponse> updateRoom(@PathVariable String id, @RequestBody UpdateRoomRequest updatedRoomDetails) {
        return updateRoomInputPort.updateRoom(id, updatedRoomDetails)
                .map(task -> new ResponseEntity<>(new UpdateRoomResponse(task), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/checkin/{idRoom}")
    public ResponseEntity<Boolean> checkInRoom(@PathVariable String idRoom) {
        return new ResponseEntity<>(this.checkInRoomInputPort.checkInRoom(idRoom), HttpStatus.OK);
    }
    
    @PutMapping("/checkout/{idRoom}")
    public ResponseEntity<Boolean> checkInOut(@PathVariable String idRoom) {
        return new ResponseEntity<>(this.checkOutRoomInputPort.checkOutRoom(idRoom), HttpStatus.OK);
    }
    
    @PostMapping("/maintenance/{idRoom}")
    public ResponseEntity<MaintenanceRoomResponse> maintenanceRoom(@PathVariable String idRoom) {
        Maintenance maintenance = maintenanceRoomInputPort.createMaintenanceRoom(idRoom);
        return new ResponseEntity<>(new MaintenanceRoomResponse(maintenance), HttpStatus.CREATED);
    }
    
    @PostMapping("/maintenance/all")
    public ResponseEntity<List<MaintenanceRoomResponse>> maintenanceRoomAll() {
        List<Maintenance> maintenances = maintenanceAllRoomsInputPort.makeMaintenanceAllRooms();
        List<MaintenanceRoomResponse> response = maintenances.stream()
                .map(MaintenanceRoomResponse::new) // Utilize the constructor for mapping
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    @RequestMapping(method = RequestMethod.HEAD, path = "/exists/{idRoom}")
    public ResponseEntity<Void> checkHotelExists(@PathVariable String idRoom) {
        if (existRoomInputPort.checkExistRoom(idRoom)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
    
}
