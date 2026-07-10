package com.ecommerce.service.impl;

import com.ecommerce.dto.request.AddressRequest;
import com.ecommerce.dto.response.AddressResponse;
import com.ecommerce.factory.AddressFactory;
import com.ecommerce.factory.UserFactory;
import com.ecommerce.mapper.AddressMapper;
import com.ecommerce.model.Address;
import com.ecommerce.model.User;
import com.ecommerce.repository.AddressRepository;
import com.ecommerce.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;
    private final AddressFactory addressFactory;
    private final UserFactory userFactory;

    @Override
    public AddressResponse addAddress(Long userId, AddressRequest request) {
        User user = userFactory.getUserById(userId);
        Address address = addressFactory.createAddress(request);
        address.setUser(user);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    public AddressResponse updateAddress(Long addressId, AddressRequest request) {
        Address address = addressFactory.getAddressById(addressId);
        addressMapper.updateEntity(request, address);
        return addressMapper.toResponse(addressRepository.save(address));
    }

    @Override
    public void deleteAddress(Long addressId) {
        Address address = addressFactory.getAddressById(addressId);
        addressRepository.delete(address);
    }

    @Override
    public AddressResponse getAddressById(Long addressId) {
        return addressMapper.toResponse(
                addressFactory.getAddressById(addressId)
        );
    }

    @Override
    public List<AddressResponse> getUserAddresses(Long userId) {
        User user = userFactory.getUserById(userId);
        return addressRepository.findByUser(user)
                .stream()
                .map(addressMapper::toResponse)
                .toList();
    }
}