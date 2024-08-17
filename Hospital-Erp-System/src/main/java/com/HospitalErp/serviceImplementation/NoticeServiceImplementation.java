package com.HospitalErp.serviceImplementation;

import com.HospitalErp.model.Notice;
import com.HospitalErp.repository.NoticeRepository;
import com.HospitalErp.service.NoticeService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NoticeServiceImplementation implements NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticeServiceImplementation(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Override
    public List<Notice> getNotices() {
        return noticeRepository.findAllActiveNotices();
    }
}
