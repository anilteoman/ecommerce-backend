package com.example.workintech.ecomm.mapper;



import com.example.workintech.ecomm.dto.AddressRequest;
import com.example.workintech.ecomm.dto.AddressResponse;
import com.example.workintech.ecomm.dto.AddressUpdateRequest;
import com.example.workintech.ecomm.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequest addressRequest) {
        Address address = new Address();
        address.setTitle(addressRequest.title());
        address.setName(addressRequest.name());
        address.setSurname(addressRequest.surname());
        address.setPhone(addressRequest.phone());
        address.setCity(addressRequest.city());
        address.setDistrict(addressRequest.district());
        address.setNeighborhood(addressRequest.neighborhood());
        address.setAddress(addressRequest.address());
        return address;
    }

    public AddressResponse toResponse(Address address) {
        return new AddressResponse(address.getId(), address.getTitle(), address.getName(), address.getSurname(), address.getPhone(),address.getUser().getId(),address.getCity(), address.getDistrict(), address.getNeighborhood(), address.getAddress());
    }

    public Address toEntity(AddressUpdateRequest updateRequest) {
        Address address = new Address();
        address.setId(updateRequest.id());
        address.setTitle(updateRequest.title());
        address.setName(updateRequest.name());
        address.setSurname(updateRequest.surname());
        address.setPhone(updateRequest.phone());
        address.setCity(updateRequest.city());
        address.setDistrict(updateRequest.district());
        address.setNeighborhood(updateRequest.neighborhood());
        address.setAddress(updateRequest.address());
        return address;
    }
}
