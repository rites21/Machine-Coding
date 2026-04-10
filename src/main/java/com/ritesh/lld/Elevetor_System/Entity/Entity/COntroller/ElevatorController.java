package com.ritesh.lld.Elevetor_System.Entity.Entity.COntroller;

import java.util.List;

import com.ritesh.lld.Elevetor_System.Entity.Entity.Direction;
import com.ritesh.lld.Elevetor_System.Entity.Entity.Elevator;
import com.ritesh.lld.Elevetor_System.Entity.Entity.Floor;
import com.ritesh.lld.Elevetor_System.Entity.Entity.Request;
import com.ritesh.lld.Elevetor_System.Entity.Entity.RequestType;

public class ElevatorController {
    private List<Elevator> elevators;
    private List<Floor> floors;

    public void requestElevator(Floor floor, Direction direction) {
        //Case-1 :
        // same direction ka nearest
        int minDist = Integer.MAX_VALUE;
        Elevator elevatorReq1 = null;
        for (Elevator elevator : elevators) {
            if (elevator.getDirection().equals(direction)) {
                //up
                if (direction.equals(Direction.UP)) {
                    int dist = Math.abs(floor.getFloorNumber() - elevator.getCurrentFloor());
                    if (dist < minDist) {
                        minDist = dist;
                        if (elevator.getCurrentFloor() <= floor.getFloorNumber()) {
                            elevatorReq1 = elevator;
                        }
                    }
                } else {
                    int dist = Math.abs(floor.getFloorNumber() - elevator.getCurrentFloor());
                    if (dist < minDist) {
                        minDist = dist;
                        if (elevator.getCurrentFloor() >= floor.getFloorNumber()) {
                            elevatorReq1 = elevator;
                        }
                    }
                }
            }
        }

        //case-2
        int mindist2 = Integer.MAX_VALUE;
        Elevator elevatorReq = null;
        for (Elevator elevator : elevators) {
            if (elevator.getDirection().equals(Direction.IDLE)) {
                int dist = Math.abs(floor.getFloorNumber() - elevator.getCurrentFloor());
                if (dist < mindist2) {
                    elevatorReq = elevator;
                }
            }
        }

        //case 3
        int mindist3 = Integer.MAX_VALUE;
        Elevator elevatorReq2 = null;
        for (Elevator elevator : elevators) {
            int dist = Math.abs(floor.getFloorNumber() - elevator.getCurrentFloor());
            if (dist < mindist3) {
                elevatorReq2 = elevator;
            }
        }

        Elevator finalElevator = elevatorReq1;//will find who is no null
        if (finalElevator.getDirection().equals(Direction.IDLE)) {
            finalElevator.addStop(new Request(direction, finalElevator.getCurrentFloor(), RequestType.HALL_CALL));
        } else {
            finalElevator.addStop(new Request(finalElevator.getDirection(), finalElevator.getCurrentFloor(), RequestType.HALL_CALL));
        }
    }


    public void selectFloor(int elevatorId, Floor floor) {

    }
}

