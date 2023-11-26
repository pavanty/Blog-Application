import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ImageCheck() {
  const [datacheck, setdatacheck] = useState([]);
  const [file, setFile] = useState(null);
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    const formData = new FormData();
    formData.append("file", file);

    const values = { name, description }; // Create values object

    try {
      const response = await axios.post(
        "http://localhost:8080/imageOrFile/save",
        formData,
        {
          params: values, // Send values as query parameters
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log(response.data);
      setFile(null);
      setName("");
      setDescription("");
      getdata();
    } catch (error) {
      console.error("Error uploading image:", error);
    }
  };
  const getdata = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/imageOrFile/getall"
      );
      setdatacheck(response.data);
    } catch (error) {
      console.error("Error fetching data:", error);
      if (error.response) {
        console.error("Response:", error.response);
      }
    }
  };

  useEffect(() => {
    getdata();
  }, []);

  return (
    <div>
      <h2>Image Upload</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="file">Choose an image: </label>
          <input
            type="file"
            id="file"
            accept="image/*"
            onChange={handleFileChange}
          />
        </div>
        <div>
          <label htmlFor="name">Name: </label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </div>
        <div>
          <label htmlFor="description">Description: </label>
          <input
            type="text"
            id="description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </div>
        <button type="submit">Upload</button>
      </form>
      {datacheck.map((data) => (
        <div key={data.id}>
          <p>{data.name}</p>
          <img src={`data:image/jpeg;base64,${data.image}`} alt={data.name} />
        </div>
      ))}
    </div>
  );
}
