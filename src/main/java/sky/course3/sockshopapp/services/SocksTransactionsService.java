package sky.course3.sockshopapp.services;

import sky.course3.sockshopapp.model.SocksTransaction;

import java.io.IOException;
import java.nio.file.Path;

public interface SocksTransactionsService {
    void addSocksTransactions(SocksTransaction socksTransaction);

    Path getAllFile() throws IOException;
}
