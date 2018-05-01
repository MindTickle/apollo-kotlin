package com.apollographql.apollo.response;

import javax.annotation.Nonnull;

/**
 * Class represents the adapter for mapping GraphQL custom scalar types to Java objects.
 *
 * <h3>Defining a custom scalar type adapter</h3>
 *
 * The GraphQL specification includes the following default scalar types: <b>String, Int, Float and Boolean</b>. But
 * often use cases arise when support for custom scalar types is required and for those cases this class should be used
 * to customize conversion. Here's an example of a type adapter for scalar type {@link java.util.Date}:
 * <pre>{@code
 * CustomTypeAdapter<Date> dateCustomTypeAdapter = new CustomTypeAdapter<Date>() {
 *
 *    public Date decode(CustomTypeValue value) {
 *      try {
 *        return ISO8601_DATE_FORMAT.parse(value);
 *      } catch (ParseException e) {
 *        throw new RuntimeException(e);
 *      }
 *    }
 *
 *    public CustomTypeValue encode(Date value) {
 *      return ISO8601_DATE_FORMAT.format(value);
 *    }
 * };
 * }
 * </pre>
 * To use a custom type adapter with Apollo, you must first define the mapping in the build.gradle file:
 * <pre>{@code
 * apollo {
 *  customTypeMapping['DateTime'] = "java.util.Date"
 * }
 * }
 * </pre>
 *
 * The lines above tell the compiler which type to use while generating the classes. Once this is done, register the
 * type adapter with {@link com.apollographql.apollo.ApolloClient.Builder}:
 * <pre>{@code
 * ApolloClient.Builder builder = ApolloClient.builder()
 *  .addCustomTypeAdapter(CustomType.DATE, dateCustomTypeAdapter);
 * }
 * </pre>
 * In the example code above, the CustomType.DATE is the class generated by the compiler.
 */
public interface CustomTypeAdapter<T> {

  /**
   * De-serializes the value to the custom scalar type. Usually used in parsing the GraphQL response.
   *
   * @param value the value to de-serialize
   * @return custom scalar type
   */
  T decode(@Nonnull CustomTypeValue value);

  /**
   * Serializes the custom scalar type to the corresponding string value. Usually used in serializing variables or input
   * values.
   *
   * @param value the custom scalar type to serialize
   * @return serialized string value
   */
  @Nonnull CustomTypeValue encode(@Nonnull T value);
}
