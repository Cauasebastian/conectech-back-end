import {Box} from '@mui/material';
import { TituloCadastro, ImgLogo, LinkLogin, ParagrafoLogin } from './style';
import { stylesFormCadastro } from './style';
import OutlinedInput from '@mui/material/OutlinedInput';
//import FilledInput from '@mui/material/FilledInput';
import {Grid} from '@mui/material'
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import IconButton from '@mui/material/IconButton';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import FormControl from '@mui/material/FormControl';
import FormHelperText from '@mui/material/FormHelperText';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import Botao from '../Botao';

import AccountCircle from '@mui/icons-material/AccountCircle';
import EmailOutlinedIcon from '@mui/icons-material/EmailOutlined';
import HttpsOutlinedIcon from '@mui/icons-material/HttpsOutlined';
import DateRangeOutlinedIcon from '@mui/icons-material/DateRangeOutlined';
import WcOutlinedIcon from '@mui/icons-material/WcOutlined';

import { useState } from 'react';
import {useNavigate} from 'react-router-dom'
import {useForm} from 'react-hook-form'
import {isEmail} from 'validator';

function FormCadastro(){

    const navigate = useNavigate();
    const goToTelaLogin = () => {
        navigate('/login')
     }
    const [showPassword, setShowPassword] = useState(false);
    const handleClickShowPassword = () => setShowPassword((show) => !show)
    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    }
    
    const {
        register,
        handleSubmit,
        reset, 
        formState: { errors },
    } = useForm();

    const onSubmit = (data) => {
        alert(JSON.stringify(data))
        reset();
    }
    return(
        <Box component='div' sx={stylesFormCadastro.div} >
            <TituloCadastro>Cadastre-se</TituloCadastro>
            <Box
                component='form'
                sx={stylesFormCadastro.form}
                autoComplete='off'
                noValidate
                onSubmit={handleSubmit(onSubmit)}
                
                >
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <ImgLogo src='images/img-telainicial.png'/> 
                        </Grid>
                        
                        <Grid item xs={6}>
                            <FormControl sx={stylesFormCadastro.inputDiv} fullWidth variant='outlined'>
                            <InputLabel sx={stylesFormCadastro.inputLabel} >Nome</InputLabel>
                                <OutlinedInput
                                type='text'
                                id="outlined-adornment-amount"
                                sx={stylesFormCadastro.inputField}
                                {...register('nome', {
                                    required: true,
                                    validate: (value) => value.length >= 5,
                                   
                                    
                                })}
                                label='Nome'
                                error={errors.nome ? true : false}
                                startAdornment={
                                    <InputAdornment position="start">
                                    <AccountCircle sx={stylesFormCadastro.inputIcon} />
                                    </InputAdornment>
                                }
                                />
                                 {errors?.nome?.type === 'required' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>O nome é obrigatório.</FormHelperText> }
                                 {errors?.nome?.type === 'validate' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>O nome deve ter no mínimo 5 caracteres.</FormHelperText> }
                            </FormControl>
                        </Grid>
                        <Grid item xs={6}>
                            <FormControl sx={stylesFormCadastro.inputDiv} fullWidth variant='outlined'>
                            <InputLabel sx={stylesFormCadastro.inputLabel} >Email</InputLabel>
                                <OutlinedInput
                                id="outlined-adornment-amount"
                                type='email'
                                {...register('email', {
                                    required: true,
                                    validate: (value) => isEmail(value),
                                   
                                    
                                })}
                                error={errors.email ? true : false}
                                sx={stylesFormCadastro.inputField}
                                label='Nome'
                                startAdornment={
                                    <InputAdornment position="start">
                                    <EmailOutlinedIcon sx={stylesFormCadastro.inputIcon} />
                                    </InputAdornment>
                                }
                                />
                                {errors?.email?.type === 'required' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>O email é obrigatório.</FormHelperText> }
                                 {errors?.email?.type === 'validate' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>O email está incorreto.</FormHelperText> }
                            </FormControl>
                        </Grid>
                        <Grid item xs={4}>
                            <FormControl sx={stylesFormCadastro.inputDiv} fullWidth variant='outlined'>
                            <InputLabel sx={stylesFormCadastro.inputLabel} >Senha</InputLabel>
                                <OutlinedInput
                                id="outlined-adornment-amount"
                                type={showPassword ? 'text' : 'password'}
                                sx={stylesFormCadastro.inputField}
                                label='Nome'
                                {...register('password', {
                                    required: true, 
                                    minLength: 8,
                                    
                                })}
                                error={errors.password ? true : false}
                                startAdornment={
                                    <InputAdornment position="start">
                                    <HttpsOutlinedIcon sx={stylesFormCadastro.inputIcon} />
                                    </InputAdornment>
                                }
                                endAdornment={
                                    <InputAdornment>
                                        <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={handleClickShowPassword}
                                            onMouseDown={handleMouseDownPassword}
                                            edge='end'
                                            
                                            sx={stylesFormCadastro.inputIcon}
                                            >
                                                {showPassword ? <VisibilityOff fontSize='small' /> : <Visibility fontSize='small' />}
                
                                        </IconButton>
                                    </InputAdornment>
                                }
                                />
                                    {errors?.password?.type === 'required' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>Senha é obrigatória.</FormHelperText> }
                                    {errors?.password?.type === 'minLength' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>Senha precisa ter no mínimo 8 caracteres.</FormHelperText>}
                            </FormControl>
                        </Grid>
                        <Grid item xs={4}>
                            <FormControl sx={stylesFormCadastro.inputDiv} fullWidth variant='outlined'>
                            <InputLabel sx={stylesFormCadastro.inputLabel} >Idade</InputLabel>
                                <OutlinedInput
                                id="outlined-adornment-amount"
                                type='number'
                                sx={stylesFormCadastro.inputField}
                                {...register('idade', {
                                    required: true,
                                    validate: (value) => value>=18,
                                   
                                    
                                })}
                                label='Nome'
                                error={errors.idade ? true : false}
                                startAdornment={
                                    <InputAdornment position="start">
                                    <DateRangeOutlinedIcon sx={stylesFormCadastro.inputIcon} />
                                    </InputAdornment>
                                }
                                />
                                    {errors?.idade?.type === 'required' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>A idade é obrigatória.</FormHelperText> }
                                    {errors?.idade?.type === 'validate' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>Você precisa ter no mínimo 18 anos.</FormHelperText>}
                            </FormControl>
                        </Grid>
                        <Grid item xs={4}>
                            <FormControl sx={stylesFormCadastro.inputDiv} fullWidth variant='outlined'>
                            <InputLabel sx={stylesFormCadastro.inputLabel} >Gênero</InputLabel>
                                <Select
                                labelId="demo-simple-select-label"
                                id="demo-simple-select"
                                {...register('genero', {
                                    required: true,
                                    
                                   
                                    
                                })}
                                error={errors.genero ? true : false}
                                label='Gênero'
                                sx={stylesFormCadastro.inputField}
                                startAdornment={
                                    <InputAdornment position="start">
                                    <WcOutlinedIcon sx={stylesFormCadastro.inputIcon} />
                                    </InputAdornment>
                                }
                                >
                                    <MenuItem value={'Masculino'}>Masculino</MenuItem>
                                    <MenuItem value={'Feminino'}>Feminino</MenuItem>
                                    
                                </Select>
                                    {errors?.genero?.type === 'required' && <FormHelperText sx={stylesFormCadastro.mensagemErro}>O gênero é obrigatório.</FormHelperText> }
                                   
                            </FormControl>
                        </Grid>
                 </Grid>
                  <Grid item xs={6}>
                    <Botao type='submit'  padding='0.8rem 5rem' nome='Criar conta'/>
                  </Grid>
                 
            </Box>
            <ParagrafoLogin>Já possui conta? <LinkLogin onClick={goToTelaLogin}>Faça o login</LinkLogin></ParagrafoLogin>  
        </Box>
    )
}
export default FormCadastro