package com.ar.dev.tierra.api.mapper;

import com.ar.dev.tierra.api.model.StockBebelandia;
import com.ar.dev.tierra.api.model.StockLibertador;
import com.ar.dev.tierra.api.model.StockTierra;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Paulo
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockTierra StockBebelandiaToStockTierra(StockBebelandia stock);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockTierra StockLibertadorToStockTierra(StockLibertador stock);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockBebelandia StockLibertadorToStockBebelandia(StockLibertador stock);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockBebelandia StockTierraToStockBebelandia(StockTierra stock);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockLibertador StockTierraToStockLibertador(StockTierra stock);

    @Mappings({
        @Mapping(source = "cantidad", target = "cantidad"),
        @Mapping(source = "idProducto", target = "idProducto"),
        @Mapping(source = "estado", target = "estado"),
        @Mapping(source = "usuarioCreacion", target = "usuarioCreacion"),
        @Mapping(source = "usuarioModificacion", target = "usuarioModificacion"),
        @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
        @Mapping(source = "fechaModificacion", target = "fechaModificacion"),
        @Mapping(source = "idSucursal", target = "idSucursal")
    })
    StockLibertador StockBebelandiaToStockLibertador(StockBebelandia stock);

}
