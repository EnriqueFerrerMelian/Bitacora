package com.example.bitacora.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bitacora.fragmentos.Dispositivos;

import java.util.List;
@Dao
public interface DispositivoDao {
    // Insert new entity and password
    @Insert
    void insert(Dispositivo dispositivo);

    // Delete an Entity
    @Delete
    void delete(Dispositivo dispositivo);

    // Edit an entity
    @Update
    void update(Dispositivo dispositivo);

    //GET QUERIES
    // Get By ID
    @Query("SELECT * FROM devices WHERE id = :id LIMIT 1")
    Dispositivo getDispositivoById(int id);
    // Get devise for a specific dispositivo
    @Query("SELECT * FROM devices WHERE dispositivo = :dispositivo LIMIT 1")
    Dispositivo getDispositivo(String dispositivo);

    // Get devise for a specific modelo
    @Query("SELECT * FROM devices WHERE modelo = :modelo LIMIT 1")
    Dispositivo getModelo(String modelo);

    // Get devise for a specific fabricante
    @Query("SELECT * FROM devices WHERE modelo = :fabricante LIMIT 1")
    Dispositivo getFabricante(String fabricante);

    // Get devise for a specific ubicacion
    @Query("SELECT * FROM devices WHERE ubicacion LIKE '%' || :ubicacion || '%'")
    Dispositivo getUbicacion(String ubicacion);

    // Get devise for a specific switche
    @Query("SELECT * FROM devices WHERE switche = :switche LIMIT 1")
    Dispositivo getSwitche(String switche);

    // Get devise for a specific puertoSwitch
    @Query("SELECT * FROM devices WHERE puertoSwitch = :puertoSwitch LIMIT 1")
    Dispositivo getByPuertoSwitch(String puertoSwitch);

    // Get devise for a specific ip
    @Query("SELECT * FROM devices WHERE ip = :ip LIMIT 1")
    Dispositivo getByIp(String ip);

    // Get devise for a specific gateway
    @Query("SELECT * FROM devices WHERE gateway = :gateway LIMIT 1")
    Dispositivo getByGateway(String gateway);

    // Get devise for a specific alimentación
    @Query("SELECT * FROM devices WHERE alimentacion = :alimentacion LIMIT 1")
    Dispositivo getByAlimentación(String alimentacion);

    // Get all devices that contain the query
    @Query("SELECT * FROM devices WHERE dispositivo = :query OR modelo = :query OR fabricante = " +
            ":query OR switche = :query OR puertoSwitch = :query OR ip = :query OR gateway = :query OR mask = :query OR alimentacion = :query")
    List<Dispositivo> getAllDispositivosFromQuery(String query);
// dispositivo, modelo, fabricante, ubicacion, switche, puertoSwitch, ip, gateway, mask, alimentacion

    // Get all data
    @Query("SELECT * FROM devices")
    List<Dispositivo> getAllDispositivos();
    // Import all
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Dispositivo> dispositivos);


    //ORDER QUERIES
    // Order by dispositivo
    @Query("SELECT * FROM devices ORDER BY dispositivo ASC")
    List<Dispositivo> getAllDispositivosByDispositivo();

    // Order by modelo
    @Query("SELECT * FROM devices ORDER BY modelo ASC")
    List<Dispositivo> getAllDispositivosByModelo();

    // Order by fabricante
    @Query("SELECT * FROM devices ORDER BY fabricante ASC")
    List<Dispositivo> getAllDispositivosByFabricante();

    // Order by ubicacion
    @Query("SELECT * FROM devices ORDER BY ubicacion ASC")
    List<Dispositivo> getAllDispositivosByUbicacion();

    // Order by ip
    @Query("SELECT * FROM devices ORDER BY ip ASC")
    List<Dispositivo> getAllDispositivosByIp();

    // Order by gateway
    @Query("SELECT * FROM devices ORDER BY gateway ASC")
    List<Dispositivo> getAllDispositivosByGateway();

    // Order by alimentacion
    @Query("SELECT * FROM devices ORDER BY alimentacion ASC")
    List<Dispositivo> getAllDispositivosByAlimentacion();

    // Order by voltaje
    @Query("SELECT * FROM devices ORDER BY voltaje ASC")
    List<Dispositivo> getAllDispositivosByVoltaje();


    // Motor de búsqueda
    // Search dispositivos by dispositivos using LIKE for partial coincidence
    @Query("SELECT * FROM devices WHERE dispositivo LIKE '%' || :dispositivo || '%'")
    List<Dispositivo> searchByPlatform(String dispositivo);
}
