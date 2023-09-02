package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.Room;
import oladejo.mubarak.NiqueResortHub.data.repository.RoomRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.RoomDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    @Override
    public Room addRoom(RoomDto addRoomRequest) {

        List<Room> allRooms = getAllRooms();
        for (Room room : allRooms) {
            if (room.getRoomNumber().equals(addRoomRequest.getRoomNumber())) throw new NiqueResortHubException
                    ("A room with the number " + addRoomRequest.getRoomNumber() + " already exists, choose another room number");

        }
        Room room = new Room(addRoomRequest.getRoomNumber(),
                addRoomRequest.getRoomType(), addRoomRequest.getRoomPrice(), addRoomRequest.getRoomStatus());
        roomRepository.save(room);
        return room;
    }

    @Override
    public Room getRoomById(Long roomId) {
        return null;
    }

    @Override
    public List<Room> getAllRooms() {
        return null;
    }

    @Override
    public List<Room> getAvailableRooms() {
        return null;
    }

    @Override
    public List<Room> getBookedRooms() {
        return null;
    }

    @Override
    public Room editRoomDetails(Long roomId, RoomDto editRoomRequest) {
        return null;
    }

    @Override
    public Room getRoomByRoomNumber(String roomNumber) {
        return null;
    }

    @Override
    public void saveRoom(Room room) {

    }
}
