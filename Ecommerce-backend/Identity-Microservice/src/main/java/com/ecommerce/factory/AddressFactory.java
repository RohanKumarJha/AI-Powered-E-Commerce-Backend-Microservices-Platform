package com.ecommerce.factory;

import com.ecommerce.dto.request.AddressRequest;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.model.Address;
import com.ecommerce.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressFactory {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Address createAddress(AddressRequest request) {
        return addressMapper.toEntity(request);
    }

    public Address getAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Address",
                                "addressId",
                                addressId
                        ));
    }

    /*
     * ==========================================================
     * TODO: Future Enhancement
     * ==========================================================
     *
     * Methods:
     * - createAddress(...)
     *
     * Responsibilities:
     * - Create Address entity
     * - Set default address
     * - Handle address initialization
     */
}