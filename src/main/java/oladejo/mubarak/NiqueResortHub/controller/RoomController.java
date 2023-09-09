package oladejo.mubarak.NiqueResortHub.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.NiqueResortHub.dtos.request.RoomDto;
import oladejo.mubarak.NiqueResortHub.service.RoomServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/room/")
@CrossOrigin("*")
public class RoomController {
    private final RoomServiceImpl roomService;

    @PostMapping("add")
    public ResponseEntity<?> addRoom(@RequestBody RoomDto roomDto){
        try{
           return ResponseEntity.ok(roomService.addRoom(roomDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("find/{roomId}")
    public ResponseEntity<?> findRoom(@PathVariable Long roomId){
        try{
            return ResponseEntity.ok(roomService.getRoomById(roomId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("all")
    public ResponseEntity<?> findAllRooms(){
            return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("available")
    public ResponseEntity<?> findAllAvailableRooms(){
        return ResponseEntity.ok(roomService.getAvailableRooms());
    }
    @GetMapping("booked")
    public ResponseEntity<?> findAllBookedRooms(){
        return ResponseEntity.ok(roomService.getBookedRooms());
    }
    @PatchMapping("edit/{roomId}")
    public ResponseEntity<?> editRoomDetails(@PathVariable Long roomId, @RequestBody RoomDto roomDto){
        try{
            return ResponseEntity.ok(roomService.editRoomDetails(roomId, roomDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("find-by-number")
    public ResponseEntity<?> findRoomByRoomNumber(@RequestParam String roomNumber){
        try{
            return ResponseEntity.ok(roomService.getRoomByRoomNumber(roomNumber));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteRoom(@RequestParam Long roomId){
        try{
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok("Room deleted successfully");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
