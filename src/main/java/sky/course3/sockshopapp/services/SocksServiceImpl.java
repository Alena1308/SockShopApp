package sky.course3.sockshopapp.services;

import org.springframework.stereotype.Service;
import sky.course3.sockshopapp.model.Socks;

import java.util.*;

@Service
public class SocksServiceImpl implements SocksService {
    private static long id = 1;
    private static Map<Long, Socks> socksMap = new HashMap<>();

    @Override
    public long postNewSocks(Socks socks) {
        socksMap.put(id, socks);
        return id++;
        //Обработать исключения и ошибки
    }

    @Override
    public boolean removeSocks(Socks neededSocks) {
        long quantityOfThisSocksType = 0;
        for (Socks socks : socksMap.values()) {
            if ((neededSocks.getSize().equals(socks.getSize()))
                    || (neededSocks.getColor().equals(socks.getColor()))
                    || (neededSocks.getCottonPart() == socks.getCottonPart())) {
                quantityOfThisSocksType += socks.getQuantity();
            }
        }
        if(quantityOfThisSocksType >= neededSocks.getQuantity()) {
            setQuantityAndRemove(neededSocks.getQuantity());
            return true;
        }
        return false;
    }

    private void setQuantityAndRemove(long quantityNeeded) {
        while (quantityNeeded>0) {
            Iterator<Map.Entry<Long, Socks>> it = socksMap.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<Long, Socks> entry = it.next();
                while (entry.getValue().getQuantity()>0){
                    entry.getValue().setQuantity(entry.getValue().getQuantity()-1);
                    quantityNeeded -= 1;
                }
                if(entry.getValue().getQuantity() == 0){
                    socksMap.remove(entry.getKey());
                }
            }
        }
    }
    private void setQuantityAndRemove2(long quantityNeeded) {
        while (quantityNeeded>0) {
            for (Socks socks : socksMap.values()) {
                while (socks.getQuantity()>0){
                    socks.setQuantity(socks.getQuantity()-1);
                    quantityNeeded -= 1;
                }
                if(socks.getQuantity() == 0){
                    long key = findKeyFromMap(socks);
                    socksMap.remove(key);
                }
            }
        }
    }
    private long findKeyFromMap(Socks socks){
        for(Map.Entry<Long, Socks> pair : socksMap.entrySet()){
            if(socks.equals(pair.getValue())){
                return pair.getKey();
            }
        }
        throw new RuntimeException("Нет ключа по данному значению");
    }
}
