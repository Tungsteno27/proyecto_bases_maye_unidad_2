/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BOs;

import DAOs.ClienteDAO;
import DTOs.ClienteDTO;
import DTOs.ClienteFrecuenteDTO;
import adaptadores.ClienteFrecuenteAdapter;
import entidades.Cliente;
import entidades.ClienteFrecuente;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import factory.ClienteAdapterFactory;
import java.util.ArrayList;
import java.util.List;
import utilidadesBO.Encriptador;
import utilidadesBO.Validadores;

/**
 *
 * @author Tungs
 */
public class ClienteBO {

    //El obtener por id ya funciona, faltaría adaptar los otros métodos usando el factory
    private ClienteDAO clienteDAO;

    public ClienteBO(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void registrar(ClienteDTO dto) throws NegocioException {
        try {
            if (dto.getNombres() == null || dto.getNombres().isBlank()) {
                throw new NegocioException("El nombre es obligatorio");
            }
            if (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().isBlank()) {
                throw new NegocioException("El apellido paterno es obligatorio");
            }
            if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
                throw new NegocioException("El teléfono es obligatorio");
            }
            if (!Validadores.validarNombre(dto.getNombres())) {
                throw new NegocioException("El nombre no es válido");
            }
            if (!Validadores.validarApellido(dto.getApellidoPaterno())) {
                throw new NegocioException("El apellido no es válido");
            }
            if(dto.getApellidoMaterno() != null && !dto.getApellidoMaterno().isBlank()){
                if (!Validadores.validarApellidoMa(dto.getApellidoMaterno())) {
                throw new NegocioException("El apellido no es válido");
            }
            }
            
            if (!Validadores.validarTelefono(dto.getTelefono())) {
                throw new NegocioException("El teléfono debe tener 10 dígitos");
            }
            // Encripta el teléfono antes de persistir
            String telefonoEncriptado = Encriptador.encriptar(dto.getTelefono());
            dto.setTelefono(telefonoEncriptado);
            //La verdad hacer esto fue lo único que se me ocurrió, si se maneja otro cliente entonces se agregaría un if con instanceOf
            //que no es lo más óptimo pero ya no se como arreglarlo
            //Lo hice de tipo ClienteAdapterFactory para el uso del instanceOf
            Cliente cliente = ClienteAdapterFactory.dtoAEntidad(dto);

            clienteDAO.registrarCliente(cliente);
        } catch (PersistenciaException ex) {
            System.getLogger(ClienteBO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public void eliminar(Long idCliente) throws NegocioException {
        try {
            clienteDAO.eliminarCliente(idCliente);
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al eliminar cliente: " + e.getMessage());
        }
    }

    public ClienteDTO obtenerPorId(Long idCliente) throws NegocioException {

        Cliente cliente = clienteDAO.buscarCliente(idCliente);
        if (cliente == null) {
            return null;
        }

        ClienteDTO dto = ClienteAdapterFactory.entidadADTO(cliente);

        // Desencripta el teléfono antes de regresar el DTO
        String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
        dto.setTelefono(telefonoDesencriptado);

        return dto;
    }

    public List<ClienteDTO> obtenerTodos() throws NegocioException {
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        
        for (Cliente cliente : clientes) {

            ClienteDTO dto = ClienteAdapterFactory.entidadADTO(cliente);
            // Desencripta el teléfono de cada cliente
            String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
            dto.setTelefono(telefonoDesencriptado);

            clientesDTO.add(dto);
        }
        return clientesDTO;
    }

    public List<ClienteFrecuenteDTO> obtenerFrecuentes() throws NegocioException {
        try {
            List<ClienteFrecuente> frecuentes = clienteDAO.obtenerFrecuentes();
            List<ClienteFrecuenteDTO> frecuentesDTO = new ArrayList<>();
            //Aquí es donde entran los atributos exclusivos del cliente frecuente que no se persisten
            for (ClienteFrecuente cf : frecuentes) {
                int visitas = clienteDAO.calcularVisitas(cf.getId());
                double totalGastado = clienteDAO.calcularTotalGastado(cf.getId());
                int puntos = calcularPuntos(totalGastado);

                cf.setTotalVisitas(visitas);
                cf.setTotalGastado(totalGastado);
                cf.setPuntos(puntos);
                ClienteFrecuenteDTO dto = ClienteFrecuenteAdapter.entidadADTO(cf);
                // Desencripta el teléfono
                String telefonoDesencriptado = Encriptador.desencriptar(dto.getTelefono());
                dto.setTelefono(telefonoDesencriptado);

                frecuentesDTO.add(dto);
            }

            return frecuentesDTO;

        } catch (PersistenciaException e) {
            throw new NegocioException("Error al obtener clientes frecuentes: " + e.getMessage());
        }
    }

    /**
     * Método que calcula los puntos de un cliente frecuente, se castean a int
     * para que sean cerrados
     *
     * @param totalGastado el totalGastado del clienteFrecuente
     * @return el número entero de puntos
     */
    private int calcularPuntos(double totalGastado) {
        return (int) (totalGastado / 20);
    }

    
    /**
     * Actualiza los datos de un cliente frecuente. Valida campos obligatorios,
     * desencripta el teléfono recibido (ya viene en texto plano desde la GUI) y
     * lo vuelve a encriptar antes de persistir.
     *
     * @param dto el DTO con los datos nuevos (debe tener id)
     * @throws NegocioException si alguna validación falla o hay error en BD
     */
    public void actualizar(ClienteFrecuenteDTO dto) throws NegocioException {
        if (dto.getId() == null) {
            throw new NegocioException("El cliente no tiene un ID válido");
        }
        if (dto.getNombres() == null || dto.getNombres().isBlank()) {
            throw new NegocioException("El nombre es obligatorio");
        }
        if (dto.getApellidoPaterno() == null || dto.getApellidoPaterno().isBlank()) {
            throw new NegocioException("El apellido paterno es obligatorio");
        }
        if (dto.getTelefono() == null || dto.getTelefono().isBlank()) {
            throw new NegocioException("El teléfono es obligatorio");
        }
        if (!Validadores.validarNombre(dto.getNombres())) {
            throw new NegocioException("El nombre contiene caracteres no válidos");
        }
        if (!Validadores.validarTelefono(dto.getTelefono())) {
            throw new NegocioException("El teléfono debe tener exactamente 10 dígitos");
        }
        if (dto.getCorreo() != null && !dto.getCorreo().isBlank()
                && !Validadores.validarCorreo(dto.getCorreo())) {
            throw new NegocioException("El correo electrónico no tiene un formato válido");
        }
        try {
            // El teléfono llega en texto plano desde la GUI, lo encriptamos antes de guardar
            String telefonoEncriptado = Encriptador.encriptar(dto.getTelefono());
            dto.setTelefono(telefonoEncriptado);

            ClienteFrecuente entidad = ClienteFrecuenteAdapter.dtoAEntidad(dto);
            clienteDAO.actualizarCliente(entidad);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar cliente: " + ex.getMessage());
        }
    }

    /**
     * Busca clientes frecuentes aplicando filtros de nombre, teléfono (en texto
     * plano) y correo. El filtro de teléfono se aplica aquí porque el campo
     * está encriptado en la base de datos.
     *
     * @param nombre fragmento de nombre (null o vacío = sin filtro)
     * @param telefonoPlano fragmento de teléfono en texto plano (null o vacío =
     * sin filtro)
     * @param correo fragmento de correo (null o vacío = sin filtro)
     * @return lista de ClienteFrecuenteDTO con estadísticas calculadas
     * @throws NegocioException si ocurre un error
     */
    public List<ClienteFrecuenteDTO> buscarFrecuentesPorFiltros(
            String nombre, String telefonoPlano, String correo) throws NegocioException {
        try {
            // Traemos los que coinciden por nombre y correo (filtros que podemos hacer en BD)
            List<ClienteFrecuente> frecuentes = clienteDAO.buscarFrecuentesPorFiltros(nombre, correo);

            List<ClienteFrecuenteDTO> resultado = new ArrayList<>();

            for (ClienteFrecuente cf : frecuentes) {
                // Desencriptamos el teléfono para aplicar el filtro de teléfono si viene
                String telefonoDescifrado = Encriptador.desencriptar(cf.getTelefono());

                if (telefonoPlano != null && !telefonoPlano.isBlank()) {
                    if (!telefonoDescifrado.contains(telefonoPlano.trim())) {
                        continue; // no coincide, lo saltamos
                    }
                }

                // Calculamos estadísticas
                int visitas = clienteDAO.calcularVisitas(cf.getId());
                double totalGastado = clienteDAO.calcularTotalGastado(cf.getId());
                int puntos = (int) (totalGastado / 20);

                cf.setTotalVisitas(visitas);
                cf.setTotalGastado(totalGastado);
                cf.setPuntos(puntos);

                ClienteFrecuenteDTO dto = ClienteFrecuenteAdapter.entidadADTO(cf);
                dto.setTelefono(telefonoDescifrado); // mostramos el teléfono legible
                resultado.add(dto);
            }

            return resultado;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar clientes: " + ex.getMessage());
        }
    }

    /**
     * Registra el cliente general predeterminado si aún no existe en la BD. Si
     * ya existe, no hace nada.
     *
     * @throws NegocioException si ocurre un error al registrar
     */
    public void registrarClienteGeneral() throws NegocioException {
        try {
            // Verificamos si ya existe buscando por nombre exacto
            List<ClienteDTO> existentes = obtenerTodos();
            boolean yaExiste = existentes.stream()
                    .anyMatch(c -> "Cliente".equalsIgnoreCase(c.getNombres())
                    && "General".equalsIgnoreCase(c.getApellidoPaterno()));
            if (yaExiste) {
                return; // ya está registrado, no hacemos nada
            }

            ClienteFrecuenteDTO dto = new ClienteFrecuenteDTO();
            dto.setNombres("Cliente");
            dto.setApellidoPaterno("General");
            dto.setApellidoMaterno("");
            dto.setTelefono("0000000000");
            dto.setCorreo("");
            dto.setFechaRegistro(java.time.LocalDate.now());
            this.registrar(dto);
        } catch (NegocioException ex) {
            throw new NegocioException("Error al registrar cliente general: " + ex.getMessage());
        }
    }
}
