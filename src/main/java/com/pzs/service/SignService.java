package com.pzs.service;

import com.pzs.entity.Sign;

public interface SignService {
    public int  signCount(Sign o);
    public Sign save(Sign o) ;
    public boolean checkIsSignedToday(Sign o);
}
