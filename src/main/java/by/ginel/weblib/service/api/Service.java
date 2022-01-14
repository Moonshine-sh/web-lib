package by.ginel.weblib.service.api;

import by.ginel.weblib.dto.AbstractCreateDto;
import by.ginel.weblib.dto.AbstractGetDto;
import by.ginel.weblib.dto.AbstractUpdateDto;

import java.util.List;

public interface Service <C extends AbstractCreateDto, U extends AbstractUpdateDto, G extends AbstractGetDto> {
    void save(C createDto);
    void delete(Long id);
    void update(U updateDto);
    G getById(Long id);
    List<G> getAll();
}
