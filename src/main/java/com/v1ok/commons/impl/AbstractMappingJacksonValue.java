package com.v1ok.commons.impl;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.v1ok.commons.IRestResponse;
import java.util.Set;
import org.springframework.http.converter.json.MappingJacksonValue;

/**
 * Created by liubinduo on 2017/5/17.
 */
public abstract class AbstractMappingJacksonValue<T> extends MappingJacksonValue implements
    IRestResponse<T> {

  SimpleFilterProvider filter;


  public AbstractMappingJacksonValue(Object value) {
    super(value);
    this.filter = new SimpleFilterProvider();
    this.filter.setFailOnUnknownId(false);
    this.setFilters(this.filter);
  }

  /**
   * 过滤要输出的属性
   *
   * @param id 过滤器ID
   * @param propertyArray 属性名，在这里出现的属性将会输出。
   */
  public AbstractMappingJacksonValue<T> filterOutAllExcept(String id, String... propertyArray) {
    this.filter.addFilter(id, SimpleBeanPropertyFilter.filterOutAllExcept(propertyArray));
    return this;
  }

  /**
   * 过滤要输出的属性
   *
   * @param id 过滤器ID
   * @param properties 属性名，在这里出现的属性将会输出。
   */
  public AbstractMappingJacksonValue<T> filterOutAllExcept(String id, Set<String> properties) {
    this.filter.addFilter(id, SimpleBeanPropertyFilter.filterOutAllExcept(properties));
    return this;
  }

  /**
   * 过滤输出的属性
   *
   * @param id 过滤器ID
   * @param propertyArray 属性名，在这里出现的属性将不会输出。
   */
  public AbstractMappingJacksonValue<T> serializeAllExcept(String id, String... propertyArray) {
    this.filter.addFilter(id, SimpleBeanPropertyFilter.serializeAllExcept(propertyArray));
    return this;
  }

  /**
   * 过滤输出的属性
   *
   * @param id 过滤器ID
   * @param properties 属性名，在这里出现的属性将不会输出。
   */
  public AbstractMappingJacksonValue<T> serializeAllExcept(String id, Set<String> properties) {
    this.filter.addFilter(id, SimpleBeanPropertyFilter.serializeAllExcept(properties));
    return this;
  }
}
