package hu.neuron.service

import hu.neuron.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContactService(@Autowired contactRepository: ContactRepository) {



}