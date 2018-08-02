package org.modelmapper.module.jdk8;

import java.lang.reflect.Type;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.typetools.TypeResolver;
import org.modelmapper.internal.util.MappingContextHelper;
import org.modelmapper.spi.ConditionalConverter;
import org.modelmapper.spi.MappingContext;

/**
 * Converts  {@link Object} to {@link Optional}
 *
 * @author Chun Han Hsiao
 */
public class ToOptionalConverter implements ConditionalConverter<Object, Optional<Object>> {
  private ModelMapper modelMapper;

  public ToOptionalConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Override
  public MatchResult match(Class<?> sourceType, Class<?> destinationType) {
    return (!Optional.class.equals(sourceType) && Optional.class.equals(destinationType))
        ? MatchResult.FULL
        : MatchResult.NONE;
  }

  @Override
  public Optional<Object> convert(MappingContext<Object, Optional<Object>> mappingContext) {
    if (mappingContext.getSource() == null) {
      return Optional.empty();
    }

    Object destination = modelMapper.map(mappingContext.getSource(),
        MappingContextHelper.resolveDestinationGenericType(mappingContext));
    return Optional.ofNullable(destination);
  }
}
