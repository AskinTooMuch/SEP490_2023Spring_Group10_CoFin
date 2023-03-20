export default function TableRows({ rowsData, deleteTableRows, handleChangeRow }) {
    return (
        rowsData.map((data, index) => {
            const { species, breed, number, price, total } = data;
            return (
                <tr key={index}>
                    <td><input type="text" value={species} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={breed} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={number} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={price} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <td><input type="text" value={total} onChange={(evnt) => (handleChangeRow(index, evnt))} name="" className="form-control" /> </td>
                    <button className="btn btn-outline-danger" onClick={() => (deleteTableRows(index))}>x</button>
                </tr>
            )
        })

    )

}