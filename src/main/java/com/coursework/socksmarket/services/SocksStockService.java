package com.coursework.socksmarket.services;

import com.coursework.socksmarket.model.Color;
import com.coursework.socksmarket.model.Size;
import com.coursework.socksmarket.model.Socks;

public interface SocksStockService {
    void addSocks(Socks socks);

    Socks editSocksFromStock(Socks socks);

    int getSocks(Color color, Size size, int cottonMin, int cottonMax);

    boolean removeSocks(Socks socks);
}
