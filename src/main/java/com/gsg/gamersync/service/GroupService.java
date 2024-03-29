package com.gsg.gamersync.service;

import com.gsg.gamersync.dto.GroupDtoIn;
import com.gsg.gamersync.entity.Group;
import com.gsg.gamersync.exeption.GamerSyncException;
import com.gsg.gamersync.repository.GroupRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Slf4j
public class GroupService {
    private final GameService gameService;

    private final GroupRepository groupRepository;

    public Group getGroupById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(() -> new GamerSyncException(HttpStatus.NOT_FOUND, "Cannot find group with id = " + id));
    }
    public List<Group> getGroups(){
        return groupRepository.findAllWithGames();
    }

    public Group createGroup(GroupDtoIn groupDtoIn) {
        Group group = new Group(groupDtoIn);
        group.setGame(gameService.getGameById(groupDtoIn.getGameId()));
        return groupRepository.save(group);
    }
}
