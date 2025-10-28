package com.ista.springboot.web.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.ista.springboot.web.app.models.entity.Usuario;

public interface IUsuario extends CrudRepository<Usuario, Long>  {

}
