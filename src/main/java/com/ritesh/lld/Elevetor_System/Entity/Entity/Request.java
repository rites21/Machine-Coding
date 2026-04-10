package com.ritesh.lld.Elevetor_System.Entity.Entity;

import lombok.Data;

@Data
public class Request {
    private int floorNUmber;
    private RequestType requestType;
    private Direction direction;

    public Request(Direction direction, int currentFloor, RequestType requestType) {
    }
/*    you wrote direction is null for hall call, but i//DIR WILL BE NULL FOR hall call ONLY FOR destination
    t's the opposite. Hall calls have direction, destination requests don't. F
    ix that comment. Also Request is missing the floor number field — we discussed this earlier.*/


}


