package com.example.musicdb.service;

import com.example.musicdb.model.service.LogServiceModel;

import java.util.List;

public interface LogService {
    void createLog(Long albumId, String action);

    List<LogServiceModel> findAllLogs();
}
