package sky.course3.sockshopapp.services;

import sky.course3.sockshopapp.model.Color;
import sky.course3.sockshopapp.model.Size;
import sky.course3.sockshopapp.model.Socks;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksService {
    long postNewSocks(Socks socks);

    boolean removeSocks(Socks socks);

    long getAllContainsSocksMinMax(String color, Double size, Integer cottonMin, Integer cottonMax);

    long getAllContainsSocksMin(String color, Double size, Integer cottonMin);

    long getAllContainsSocksMax(String color, Double size, Integer cottonMax);

    boolean deleteDefectiveSocks(Socks defectiveSocks);

    Path getAllFile() throws IOException;
}
