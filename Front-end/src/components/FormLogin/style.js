import styled from "styled-components";

export const TituloLogin = styled.h2`
     color: #f3f3f3;
    font-family: "Poppins", sans-serif;
    
`

export const ImgLogo = styled.img`
    max-width: 6rem;
    height: 6rem;
       
`

export const ParagrafoCadastro = styled.p`
    color: #f3f3f3;
    font-size: 14px;
    font-family: "Poppins", sans-serif;
    
`
export const LinkCadastro = styled.a`
    text-decoration: underline;
    color: #0B7FBE;
    font-family: "Poppins", sans-serif;
    cursor: pointer;
`
export const stylesForm = {
    div: {
        textAlign:'center',
        display:'flex',
        justifyContent:'center',
        alignItems:'center',
        flexDirection:'column',
        height:'100%',
        marginBottom: '1rem',
       
        '@media (min-width:1500px)':{
             marginTop: '-2rem',
             
        }
    },
    form: {
        '& > :not(style)': { m: 1, width: '32ch' },
        display:'flex',
        flexDirection:'column',
        justifyContent:'center',
        alignItems:'center',
        padding:'2.5rem',
        border:'1px solid #f3f3f3',
        gap: '2rem',
        borderRadius:'10px',
        background: ' #7c7c7c59',
        boxShadow: '0 8px 32px 0 #1f26875e',
        backdropFilter: 'blur( 13.5px )',
        WebkitBackdropFilter: 'blur( 13.5px )',
        
        
        '@media (min-width:1500px)':{
            padding:'3rem'
        }
    },
    inputDiv:{
        m: 1, width: '35ch',
        '& label.Mui-focused': {
            color:'#6F7E8C',
        }
    },
    inputLabel: {
        color:'#f3f3f3',
        fontFamily: 'Poppins' 
    
    },
    inputField:{
        color: '#f3f3f3',
        fontFamily: 'Poppins' ,
        '&::before': {
        borderBottom: '2px solid #E0E3E7'},
        '&:hover:not(.Mui-disabled, .Mui-error):before': {
            borderBottom: '2px solid #B2BAC2',
          },
        '&.Mui-focused:after': {
            borderBottom: '2px solid #6F7E8C ',
          } 
    },
    inputButtom:{
        color:'#f3f3f3'
    },
    mensagemErro:{
        color: '#f44336'
    }

}

