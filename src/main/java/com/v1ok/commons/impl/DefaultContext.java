package com.v1ok.commons.impl;


import com.v1ok.auth.IContext;
import com.v1ok.auth.IUserContext;
import lombok.ToString;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by liubinduo on 2017/5/16.
 */
@ToString
public class DefaultContext extends AbstractContext implements IContext {


  public DefaultContext(IUserContext userContext) {
    super(userContext);
  }


  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

}
