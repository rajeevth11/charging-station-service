package api.ev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T>
{
    public ResponseWrapper( String statusCode, String message, T... data )
    {
        this.setStatus(statusCode);
        this.setMessage( message );
        this.setData( Arrays.asList( data ) );
    }

    public ResponseWrapper( String statusCode, String message )
    {
        this.setStatus(statusCode);
        this.setMessage( message );
        this.setData( null );
    }

    public static final String SUCCESS = "Success";
    public static final String ERROR = "Error";
    public static final String WARNING = "Warning";
    private String status;
    private String message;
    protected List<T> data;


}
