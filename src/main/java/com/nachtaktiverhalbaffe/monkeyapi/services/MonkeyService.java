package com.nachtaktiverhalbaffe.monkeyapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.nachtaktiverhalbaffe.monkeyapi.domain.Monkey;

public interface MonkeyService {

    public Optional<Monkey> create(Optional<MultipartFile> image, Monkey monkey, Optional<String> speciesName);

    public List<Monkey> getAll();

    public Page<Monkey> getAll(Pageable pageable);

    public Optional<Monkey> get(String name);

    public Optional<Monkey> get(Long id);

    public Optional<Monkey> update(Optional<MultipartFile> image, Monkey monkey);

    public void delete(String name);

    public void delete(Long id);

    public void delete(Monkey monkey);

    public boolean exists(String name);

    public boolean exists(Monkey monkey);

    public boolean exists(Long id);

}
