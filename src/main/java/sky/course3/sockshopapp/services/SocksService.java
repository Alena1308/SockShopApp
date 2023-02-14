package sky.course3.sockshopapp.services;

import sky.course3.sockshopapp.model.Socks;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksService {
    Object sortSocks(Socks newSocks);

    long postNewSocks(Socks socks);

    boolean removeSocks(Socks socks);

    long getAllContainsSocksMinMax(String color, Double size, Integer cottonMin, Integer cottonMax);

    long getAllContainsSocksMin(String color, Double size, Integer cottonMin);

    long getAllContainsSocksMax(String color, Double size, Integer cottonMax);

    boolean deleteDefectiveSocks(Socks defectiveSocks);

    Path getAllFile() throws IOException;

    Path getAllFileIn() throws IOException;

    Path getAllFileOut() throws IOException;
}
