package com.pzs.serviceimpl;

import com.pzs.dao.SignRepository;
import com.pzs.entity.Sign;
import com.pzs.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SignServiceImpl implements SignService {

    @Autowired
    private SignRepository signRepository;

    @Override
    public int signCount(Sign o) {
        if(o==null){
            return -1;
        }
        List<Sign> list = signRepository.findByNumAndName(o.getNum(),o.getName());
        return list.size();
    }

    @Override
    public Sign save(Sign o) {
        if(o==null){
            return null;
        }
        Sign returnSign = signRepository.save(o);
        if(returnSign == null){
            return  null;
        }
        return returnSign;
    }

    @Override
    public boolean checkIsSignedToday(Sign o) {
        List<Sign> list = signRepository.findByNumAndName(o.getNum(),o.getName());
        ArrayList<String> arrayList = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(Sign s : list) {
            arrayList.add(dateFormat.format(s.getTime()));
        }
        String thisSignDate = dateFormat.format(o.getTime());
        if(arrayList.contains(thisSignDate)){
            return true;
        }
        return false;
    }
}
