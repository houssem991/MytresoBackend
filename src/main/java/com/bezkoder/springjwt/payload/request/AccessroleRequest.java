package com.bezkoder.springjwt.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class AccessroleRequest {
  private Integer roleid;
  private Integer accesid;
  private boolean ajouter;
  private boolean modifier;
  private boolean supprimer;
  private boolean consulter;



}
