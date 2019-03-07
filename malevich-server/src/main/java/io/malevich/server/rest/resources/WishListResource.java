package io.malevich.server.rest.resources;

import com.yinyang.core.server.rest.RestResource;
import io.malevich.server.domain.WishListEntity;
import io.malevich.server.services.wishlist.WishListService;
import io.malevich.server.transfer.PageRequestDto;
import io.malevich.server.transfer.PageResponseDto;
import io.malevich.server.transfer.WishListDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/wishlist")
public class WishListResource extends RestResource<WishListDto, WishListEntity> {

    @Autowired
    private WishListService wishListService;

    public WishListResource() {
        super(WishListDto.class, WishListEntity.class);
    }

    @PreAuthorize("hasRole('ROLE_TRADER')")
    @PostMapping("/addWish")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> save(@RequestBody WishListDto dto) {
        this.wishListService.save(convertToEntity(dto));
        return ResponseEntity.ok().build();
    }

    //@PreAuthorize("hasRole('ROLE_TRADER')")
    @PostMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public PageResponseDto list(@RequestBody PageRequestDto requestDto) {
        Page<WishListEntity> resultPage = this.wishListService.findAllPageable(PageRequest.of(requestDto.getPage(), requestDto.getSize()));
        return new PageResponseDto(resultPage.getContent().stream().map(pageEntry -> convertToDto(pageEntry)).collect(Collectors.toList()), resultPage.getTotalElements(), resultPage.getTotalPages(), requestDto.getSort());
    }

    @GetMapping("/list_all")
    @ResponseStatus(HttpStatus.OK)
    public List<WishListDto> listAll() {
        List<WishListEntity> listEntities = this.wishListService.findAll();
        return convertListOfDto(listEntities);
    }

    @DeleteMapping("/remove/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        this.wishListService.delete(id);
        return ResponseEntity.ok().build();
    }

}
