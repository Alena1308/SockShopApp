package sky.course3.sockshopapp.services;

import sky.course3.sockshopapp.model.Socks;

public interface SocksService {
    long postNewSocks(Socks socks);

    boolean removeSocks(Socks socks);
}
