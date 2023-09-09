package oladejo.mubarak.NiqueResortHub.service;

import oladejo.mubarak.NiqueResortHub.data.model.Room;
import oladejo.mubarak.NiqueResortHub.dtos.request.RoomDto;

import java.util.List;

public interface RoomService {
    Room addRoom(RoomDto addRoomRequest);
    Room getRoomById(Long roomId);
    List<Room> getAllRooms();
    List<Room> getAvailableRooms();
    List<Room> getBookedRooms();
    Room editRoomDetails( Long roomId, RoomDto editRoomRequest);
    void deleteRoom(Long roomId);

    Room getRoomByRoomNumber(String roomNumber);

    void saveRoom(Room room);

}
