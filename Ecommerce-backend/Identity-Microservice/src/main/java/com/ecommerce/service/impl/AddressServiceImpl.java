package com.ecommerce.service.impl;

import com.ecommerce.dto.request.AddressRequest;
import com.ecommerce.dto.response.AddressResponse;
import com.ecommerce.factory.AddressFactory;
import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.repository.UserRepository;
import com.ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    private final AddressMapper addressMapper;
    private final AddressFactory addressFactory;

    @Override
    public AddressResponse addAddress(Long userId, AddressRequest request) {
        return null;
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        return null;
    }

    @Override
    public void deleteAddress(Long addressId) {

    }

    @Override
    public AddressResponse getAddressById(Long addressId) {
        return null;
    }

    @Override
    public List<AddressResponse> getUserAddresses(Long userId) {
        return List.of();
    }

}