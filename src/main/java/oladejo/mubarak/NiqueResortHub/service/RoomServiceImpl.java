package oladejo.mubarak.NiqueResortHub.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.NiqueResortHub.data.model.Room;
import oladejo.mubarak.NiqueResortHub.data.model.RoomStatus;
import oladejo.mubarak.NiqueResortHub.data.repository.RoomRepository;
import oladejo.mubarak.NiqueResortHub.dtos.request.RoomDto;
import oladejo.mubarak.NiqueResortHub.exception.NiqueResortHubException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;

    private final ModelMapper modelMapper;
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
        return roomRepository.findById(roomId).orElseThrow(() -> new NiqueResortHubException("room with the id " + roomId + " not found"));
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        List<Room> allRooms = getAllRooms();
        for (Room room : allRooms) {
            if (room.getRoomStatus().equals(RoomStatus.UNBOOKED)) {
                availableRooms.add(room);
            }

        }
        return availableRooms;
    }

    @Override
    public List<Room> getBookedRooms() {
        List<Room> unAvailableRooms = new ArrayList<>();
        List<Room> allRooms = getAllRooms();
        for (Room room : allRooms) {
            if (room.getRoomStatus().equals(RoomStatus.BOOKED)) {
                unAvailableRooms.add(room);
            }
        }
        return unAvailableRooms;
    }

    @Override
    public Room editRoomDetails(Long roomId, RoomDto editRoomRequest) {
        Room foundRoom = getRoomById(roomId);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(editRoomRequest, foundRoom);
        return roomRepository.save(foundRoom);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.delete(getRoomById(roomId));
    }

    @Override
    public Room getRoomByRoomNumber(String roomNumber) {
        return roomRepository.findRoomByRoomNumber(roomNumber).orElseThrow(()-> new
                NiqueResortHubException("room with number "+roomNumber+" does not exist"));
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }
}
