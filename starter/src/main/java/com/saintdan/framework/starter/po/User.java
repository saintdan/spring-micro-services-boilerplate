package com.saintdan.framework.starter.po;

import com.saintdan.framework.common.constant.CommonsConstant;
import com.saintdan.framework.common.param.BaseParam;
import com.saintdan.framework.common.tools.UUIDGenId;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tk.mybatis.mapper.annotation.KeySql;

/**
 * Authorized users, provide for spring security oauth2.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/23/15
 * @see {@link UserDetails}
 * @since JDK1.8
 */
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseParam implements UserDetails {

  private static final long serialVersionUID = 2680591198337929454L;

  @Id
  @KeySql(genId = UUIDGenId.class)
  @Column(name = "id", updatable = false)
  private String id;

  @Column(name = "name")
  private String name;

  @Column(name = "usr", nullable = false)
  private String usr;

  @Column(name = "pwd", nullable = false)
  private String pwd;

  @Builder.Default
  @Column(name = "is_account_non_expired_alias", nullable = false)
  private Boolean isAccountNonExpiredAlias = true;

  @Builder.Default
  @Column(name = "is_account_non_locked_alias", nullable = false)
  private Boolean isAccountNonLockedAlias = true;

  @Builder.Default
  @Column(name = "is_credentials_non_expired_alias", nullable = false)
  private Boolean isCredentialsNonExpiredAlias = true;

  @Builder.Default
  @Column(name = "is_enabled_alias", nullable = false)
  private Boolean isEnabledAlias = true;

  @Builder.Default
  @Column(name = "status", nullable = false)
  private Integer status = 1;

  @Column(name = "description")
  private String description;

  // Last login time
  @Column(name = "last_login_at")
  private Long lastLoginAt;

  // Last login IP address
  @Column(name = "ip")
  private String ip;

  @Column(name = "authority_str")
  private String authorityStr;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Long createdAt;

  @Column(name = "created_by", nullable = false, updatable = false)
  private String createdBy;

  @Column(name = "last_modified_at", nullable = false)
  private Long lastModifiedAt;

  @Column(name = "last_modified_by", nullable = false)
  private String lastModifiedBy;

  @Column(name = "version", nullable = false)
  private Integer version;

  /**
   * Get the authorities.
   *
   * @return GrantedAuthorities
   */
  @Override public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(
        authorityStr.split(CommonsConstant.COMMA))
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  @Override public String getUsername() {
    return getUsr();
  }

  @Override public String getPassword() {
    return getPwd();
  }

  @Override public boolean isAccountNonExpired() {
    return isAccountNonExpiredAlias;
  }

  @Override public boolean isAccountNonLocked() {
    return isAccountNonLockedAlias;
  }

  @Override public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpiredAlias;
  }

  @Override public boolean isEnabled() {
    return isEnabledAlias;
  }
}
