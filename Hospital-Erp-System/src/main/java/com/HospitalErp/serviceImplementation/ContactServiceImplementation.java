package com.HospitalErp.serviceImplementation;

import com.HospitalErp.model.Contact;
import com.HospitalErp.repository.ContactRepository;
import com.HospitalErp.service.ContactService;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImplementation implements ContactService {

    private final ContactRepository contactRepository;

    public ContactServiceImplementation(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact saveContact(Contact newContact) {
        contactRepository.save(newContact);
        return newContact;
    }
}
