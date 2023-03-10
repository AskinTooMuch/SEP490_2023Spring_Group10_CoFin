import {
    Dialog,
    DialogContent,
    Fade,
    Grid,
    IconButton,
    Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import CloseIcon from '@mui/icons-material/Close';
import React, { forwardRef } from "react";

const Transition = forwardRef(function Transition(props, ref) {
    return <Fade ref={ref} {...props} />;
});

function ConfirmBox({ open, closeDialog, title, deleteFunction }) {
    return (
        <Dialog
            fullWidth
            open={open}
            maxWidth="md"
            scroll="body"
            onClose={closeDialog}
            onBackdropClick={closeDialog}
            TransitionComponent={Transition}
        >
            <DialogContent sx={{ px: 8, py: 6, position: "relative" }}>
                <IconButton
                    size="medium"
                    onClick={closeDialog}
                    sx={{ position: "absolute", right: "1rem", top: "1rem" }}
                >
                    <CloseIcon />
                </IconButton>
                <Grid container spacing={6}>
                    <Grid item xs={12}>
                        <Box
                            sx={{
                                mb: 3,
                                display: "flex",
                                justifyContent: "flex-start",
                                flexDirection: "column",
                            }}
                        >
                            <Typography variant="h5">Xoá {title}</Typography>

                            <Typography variant="body1">
                                Bạn có chắc chắn muốn xoá {title} ?
                            </Typography>
                        </Box>
                    </Grid>
                    <Grid
                        item
                        xs={12}
                        sx={{ display: "flex", justifyContent: "flex-end", gap: "1rem" }}
                    >
                        <button onClick={closeDialog} size="medium" className="btn btn-light" >
                            Huỷ
                        </button>
                        <button onClick={deleteFunction} size="medium" className="btn btn-light">
                            Xoá
                        </button>{" "}
                    </Grid>
                </Grid>
            </DialogContent>
        </Dialog>
    );
}

export default ConfirmBox;